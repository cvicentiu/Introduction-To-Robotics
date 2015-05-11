%cd /Users/ioanacroitoru/Desktop/robot/rvctools

%run('startup_rvc.m')

% intial position
x_initial = 10;
y_initial = 10;

car_width = 2;
car_length = 4;

% distance between cars (y axis)
lateral_distance = 1;
% distance between cars (x axis)
forward_distance = 1;

% coding
x0 = [x_initial y_initial 0];
x_interm = x0(1) - (car_length+forward_distance)/2;
y_interm = x0(2) - (car_width+lateral_distance)/2;

x_final = x0(1) - (car_length + forward_distance);
y_final = x0(2) - (car_width+lateral_distance);

xg = [x_interm y_interm pi/4];

r = sim('sl_drivepose');
y = r.find('yout');

x0 = [x_interm y_interm pi/4];
xg = [x_final y_final 0];

r = sim('sl_drivepose');
y2 = r.find('yout');

yf(:,1) = [y(:,1);y2(:,1)];
yf(:,2) = [y(:,2);y2(:,2)];
plot(yf(:,1), yf(:,2))
y
%exit
