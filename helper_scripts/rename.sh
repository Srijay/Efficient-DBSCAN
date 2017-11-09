#!/bin/bash

for f in *.out; do
  first=${f%.out}
  mv $f $first
done

