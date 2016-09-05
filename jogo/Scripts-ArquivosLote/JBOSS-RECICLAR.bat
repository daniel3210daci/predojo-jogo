echo  -------------------------------------------------------------
echo  -------------------------------------------------------------
echo  -------------------------------------------------------------
echo  Reciclando ambiente

rd /S/Q D:\Desenvolvimento\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\log
rd /S/Q D:\Desenvolvimento\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\tmp
rd /S/Q D:\Desenvolvimento\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\data
rd /S/Q D:\Desenvolvimento\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments

mkdir D:\Desenvolvimento\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments
copy /Y D:\Desenvolvimento\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments.limpo\*.* D:\Desenvolvimento\Jboss\Jboss_as_711Final\jboss-as-7.1.1.Final\standalone\deployments

pause
