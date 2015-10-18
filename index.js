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

io.on('connection', function(socket) {
	console.log(socket.id);
	socket.on('new_user', function(user) {
		var user = JSON.parse(user.toString());
		console.log(user);
		obj_users[user.idphone] = user;
		socket.emit('confirm', user);
	});

	socket.on('location', function(user) {
		var user = JSON.parse(user.toString());
		var users = [];
		obj_users[user.idphone] = user;
		//socket.username = user.user;
		_.each(obj_users, function(val, key) {
			user.push(val)
		});
		console.log(users);
		socket.broadcast.emit('friends', users);
	});

});