@echo off

set nnn=%1

multichain-cli med -rpcport=1111 -rpcpassword=root -datadir=node1 create stream %nnn% true

multichain-cli med -rpcport=1111 -rpcpassword=root -datadir=node1 subscribe %nnn%

