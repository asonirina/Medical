@echo off
set /p n="Enter No: "

multichaind med -rpcport=%n%%n%%n%1 -rpcpassword=root -rpcallowip=0.0.0.0/0 -port=%n%%n%%n%2 -daemon -datadir=node%n% -reindex=1

pause