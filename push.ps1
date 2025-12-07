# Script SIMPLE para Push Rápido
# Solo ejecuta: .\push.ps1

Write-Host "🚀 Subiendo cambios a Git..." -ForegroundColor Cyan
Write-Host ""

Set-Location "C:\Users\Usuario\AndroidStudioProjects\SimpleRest"

git add .
git commit -m "feat: Proyecto SimpleRest completado con arquitectura MVVM y Jetpack Compose"
git push origin main

if ($LASTEXITCODE -ne 0) {
    Write-Host "Intentando con rama master..." -ForegroundColor Yellow
    git push origin master
}

Write-Host ""
Write-Host "✅ ¡Listo!" -ForegroundColor Green

