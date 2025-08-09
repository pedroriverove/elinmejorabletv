param(
    [string]$Path = ".",
    [string]$OutputFile = $null
)

# --- MODIFICACIÓN PRINCIPAL ---
# Carpetas a excluir para un proyecto Kotlin/Android (nombres exactos)
$excludedFolders = @('build', '.gradle', '.idea', '.git', 'captures')

function Get-Tree {
    param(
        [string]$directory,
        [string]$indent = "",
        [bool]$isLast = $true
    )

    $directoryInfo = Get-Item $directory
    $name = $directoryInfo.Name

    # Determinar el prefijo de indentación (usando caracteres ASCII)
    $prefix = if ($isLast) { "\---" } else { "+---" }

    # Mostrar el nombre del directorio actual
    $line = "$indent$prefix $name"
    if ($OutputFile) {
        $line | Out-File -FilePath $OutputFile -Append -Encoding utf8
    } else {
        $line
    }

    # Actualizar indentación para los elementos hijos
    $newIndent = if ($isLast) { "$indent    " } else { "$indent|   " }

    # Obtener subdirectorios excluyendo los no deseados
    $subDirs = @(Get-ChildItem -Path $directory -Directory |
                   Where-Object { $excludedFolders -notcontains $_.Name } |
                   Sort-Object Name)

    # Obtener y ordenar los archivos
    $files = @(Get-ChildItem -Path $directory -File | Sort-Object Name)

    # Combinar directorios y archivos para un procesamiento unificado
    $items = $subDirs + $files

    # Procesar todos los elementos hijos (directorios y archivos)
    for ($i = 0; $i -lt $items.Count; $i++) {
        $item = $items[$i]
        $isLastItem = ($i -eq ($items.Count - 1))

        if ($item -is [System.IO.DirectoryInfo]) {
            # Es un directorio, llamada recursiva
            Get-Tree -directory $item.FullName -indent $newIndent -isLast $isLastItem
        } else {
            # Es un archivo, imprimirlo
            $filePrefix = if ($isLastItem) { "\---" } else { "+---" }
            $fileLine = "$newIndent$filePrefix $($item.Name)"
            if ($OutputFile) {
                $fileLine | Out-File -FilePath $OutputFile -Append -Encoding utf8
            } else {
                $fileLine
            }
        }
    }
}

# Iniciar el proceso
if ($OutputFile -and (Test-Path $OutputFile)) {
    Remove-Item $OutputFile -Force
}

# Imprimir el directorio raíz y luego llamar a la función para sus hijos
$rootName = (Get-Item $Path).Name
if ($OutputFile) {
    $rootName | Out-File -FilePath $OutputFile -Encoding utf8
} else {
    $rootName
}

# Obtener y procesar los elementos del directorio raíz
$rootSubDirs = @(Get-ChildItem -Path $Path -Directory | Where-Object { $excludedFolders -notcontains $_.Name } | Sort-Object Name)
$rootFiles = @(Get-ChildItem -Path $Path -File | Sort-Object Name)
$rootItems = $rootSubDirs + $rootFiles

for ($i = 0; $i -lt $rootItems.Count; $i++) {
    $item = $rootItems[$i]
    $isLast = ($i -eq ($rootItems.Count - 1))

    if ($item -is [System.IO.DirectoryInfo]) {
        Get-Tree -directory $item.FullName -indent "" -isLast $isLast
    } else {
        $filePrefix = if ($isLast) { "\---" } else { "+---" }
        $fileLine = "$filePrefix $($item.Name)"
        if ($OutputFile) {
            $fileLine | Out-File -FilePath $OutputFile -Append -Encoding utf8
        } else {
            $fileLine
        }
    }
}