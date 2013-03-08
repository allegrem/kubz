#! /bin/sh
/usr/bin/socat TCP-LISTEN:4161,fork,reuseaddr FILE:/dev/ttyUSB0,b38400,raw
