#The webserver. Super cool stuff.

import http.server
import socketserver
from urllib.parse import parse_qs

class StupidHTTPRequestHandler(http.server.BaseHTTPRequestHandler):
	def do_GET(self):
		#Get parameters from request:
		params = parse_qs(self.path.split("?")[1])
		print(params)
		self.send_response(200)
		self.send_header("Content-type", "text/plain")
		self.end_headers()
		self.wfile.write(bytes("Hello, world!", "UTF-8"))
		

httpd = socketserver.TCPServer(("", 8000), StupidHTTPRequestHandler)

print("Starting at port 8000")
httpd.serve_forever()
