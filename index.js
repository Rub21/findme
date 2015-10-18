var express = require('express');
var app = express();
var server = require('http').createServer(app);
var io = require('socket.io')(server);
var _ = require('underscore');
var port = process.env.PORT || 3000;

server.listen(port, function() {
	console.log('Server listening at port %d', port);
});

// page
app.use(express.static(__dirname + '/public'));

var obj_users = {};
var users = [];


io.on('connection', function(socket) {
	console.log(socket.id);
	socket.on('new_user', function(user) {
		var user = JSON.parse(user.toString());
		console.log("New User : " + user.user);
		//test
		if (user.idphone == "b1887c22a5d7bd47") {
			user.lat = -13.151537596771284;
			user.lng = -74.2176728120173;
		}
		//socket.username = user.user;
		obj_users[user.idphone] = user;
		users = [];
		_.each(obj_users, function(val, key) {
			users.push(val)
		});
		//console.log(users);
		socket.emit('confirm', user);
		console.log("=========enviar")
		console.log(users);
		socket.emit('friends', users);
	});

	socket.on('location', function(user) {
		var user = JSON.parse(user.toString());
		users = [];
		obj_users[user.idphone] = user;
		//socket.username = user.user;
		_.each(obj_users, function(val, key) {
			users.push(val);
		});
		console.log(users);
		socket.broadcast.emit('friends', users);
	});
});