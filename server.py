#The webserver. Super cool stuff.

import http.server
import socketserver
from urllib.parse import parse_qs

class StupidHTTPRequestHandler(http.server.BaseHTTPRequestHandler):
	def do_GET(self):
		#Get parameters from request:
		params = self.path.split("?")
		params = parse_qs(params[1] if len(params) > 1 else "")
		print(params)

		#Respond
		self.send_response(200)
		self.send_header("Content-type", "text/plain")
		self.end_headers()
		self.wfile.write(bytes("Hello, world!", "UTF-8"))
		

httpd = socketserver.TCPServer(("", 8000), StupidHTTPRequestHandler)

print("Starting at port 8000")
httpd.serve_forever()
