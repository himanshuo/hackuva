#!/bin/bash

git add --all
date=`date`
git commit -m "Auto commit at $date"
git push -u origin master
