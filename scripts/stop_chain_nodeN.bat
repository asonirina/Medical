@echo off
set /p n="Enter No: "

multichain-cli med -datadir=node%n% -rpcport=%n%%n%%n%1 -rpcpassword=root -rpcallowip=0.0.0.0/0 stop 

pause