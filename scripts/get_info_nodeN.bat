@echo off
set /p n="Enter No: "

multichain-cli med -rpcport=%n%%n%%n%1 -rpcpassword=root -rpcallowip=0.0.0.0/0 -datadir=node%n% getinfo

pause