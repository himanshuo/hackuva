#The webserver. Super cool stuff.

import http.server

Handler = http.server.SimpleHTTPRequestHandler
httpd = socketserver.TCPServer(("", 8000), Handler)

print("Starting at port 8000")
httpd.serve_forever()
