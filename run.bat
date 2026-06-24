@echo off
echo ===================================================
echo   FutsalKu - Sistem Reservasi Lapangan Futsal
echo ===================================================
echo.
echo [1/2] Mengkompilasi program...
if not exist bin mkdir bin
javac -cp "lib/mysql-connector-j-9.7.0.jar" -sourcepath src/main/java -d bin src/main/java/com/futsalku/Main.java
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Gagal mengkompilasi program! Pastikan JDK 17+ sudah terinstal dan terdaftar di PATH lingkungan (Environment Variables).
    pause
    exit /b %errorlevel%
)

echo.
echo [2/2] Menjalankan program...
java -cp "bin;lib/mysql-connector-j-9.7.0.jar" com.futsalku.Main
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Terjadi kesalahan saat menjalankan program.
    echo Pastikan MySQL (XAMPP) sudah aktif dan kredensial di DatabaseConnection.java sudah sesuai.
    pause
)
