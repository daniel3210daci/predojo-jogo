rd /S/Q C:\Amil Pre-dojo\jogo-ear\target
c:
cd \
cd C:\Amil Pre-dojo\jogo
call mvn clean install package -Dmaven.test.skip=true
call mvn eclipse:eclipse
echo  -------------------------------------------------------------
echo  -------------------------------------------------------------
echo  -------------------------------------------------------------
echo  Reciclando ambiente

rd /S/Q C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\log
rd /S/Q C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\tmp
rd /S/Q C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\data
rd /S/Q C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments

mkdir C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments
copy /Y C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments.limpo\*.* C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments\

echo  ------------------------------------------------------------
echo  ------------------------------------------------------------
echo  ------------------------------------------------------------
echo  Copiando app

copy /Y C:\Amil Pre-dojo\jogo-ear\target\jogo.ear C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments


echo  -----------------------------------------------------------
echo  -----------------------------------------------------------
echo  -----------------------------------------------------------
echo  Start Jboss


C:\Amil Pre-dojo\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\bin\standalone.bat
pause
