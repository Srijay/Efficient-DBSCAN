
f1 = open("snapshots_clusters/snapshots_snapshot0", "r")
f2 = open("snapshots_clusters/snapshots_snapshot0", "r")

ds1 = {}
ds2 = {}

for line in f1:
  lsplit = line.split()
  key = lsplit[0]
  if key in ds1:
    ds1[key].add(lsplit[1])
  else:
    ds1[key] = {lsplit[1]}
    
for line in f2:
  lsplit = line.split()
  key = lsplit[0]
  if key in ds2:
    ds2[key].add(lsplit[1])
  else:
    ds2[key] = {lsplit[1]}
    

for i in ds1.keys():
  flag = 0
  for j in ds2.keys():
    if(ds1[i] == ds2[j]):
      flag = 1
  if(flag == 0):
    print("Unmatch somewhere")
  else:
    print("Perfect Match")
    
