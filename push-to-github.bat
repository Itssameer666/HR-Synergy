@echo off
title Push to GitHub - HR-Synergy
cd /d "%~dp0"
echo ========================================================
echo Pushing HRMS to GitHub: https://github.com/Itssameer666/HR-Synergy
echo ========================================================
git branch -M main
git push -u origin main --force
echo.
if %errorlevel% equ 0 (
    echo ========================================================
    echo [SUCCESS] Code successfully pushed to GitHub!
    echo ========================================================
) else (
    echo ========================================================
    echo [ERROR] Push failed. Please check your internet or login.
    echo ========================================================
)
pause
