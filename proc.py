import subprocess
p = subprocess.Popen(["/Applications/MATLAB_R2014a.app/bin/matlab","-nodisplay","-nodesktop","-nosplash","<test.m"], stdout=subprocess.PIPE)
output, err = p.communicate()
print  output
