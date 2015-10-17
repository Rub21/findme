var express = require('express');
var app = express();
var server = require('http').createServer(app);
var io = require('socket.io')(server);
var port = process.env.PORT || 3000;

server.listen(port, function() {
	console.log('Server listening at port %d', port);
});

// page
app.use(express.static(__dirname + '/public'));

var users = {};

io.on('connection', function(socket) {
	console.log(socket.id);
	socket.on('new_user', function(user) {
		console.log(user.idphone)
		var user = JSON.parse(user.toString());
		console.log(user);
		console.log(user.idphone);
		socket.username = user.user;
		users[user.idphone] = user;

		socket.emit('confirm', users);
	});

	socket.on('location', function(user) {
		var user = JSON.parse(user.toString());
		console.log(user);
		socket.username = user.user;
		users[user.idphone] = user;
	});

});