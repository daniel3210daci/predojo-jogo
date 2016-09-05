rd /S/Q  C:\Amil Pre-dojo\jogo-ear\target
c:
cd \
cd C:\Amil Pre-dojo\jogo
call mvn clean install package -X -Dmaven.test.skip=true 
call mvn eclipse:eclipse
pause
explorer C:\Amil Pre-dojo\jogo-ear\target


