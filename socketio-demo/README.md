# Socket IO Java Socket Close Issue

### Scenario

The java framework socketio-client may cause IllegalStateException when you call Socker.close() method manually, it's proved it's the bug from socketio-client.

My workbench is shown as below:

Socket IO Server: 

```xml
<dependency>
  <groupId>com.corundumstudio.socketio</groupId>
  <artifactId>netty-socketio</artifactId>
  <version>1.7.18</version>
</dependency>
```

Socket IO Client:

```xml
<dependency>
  <groupId>io.socket</groupId>
  <artifactId>socket.io-client</artifactId>
  <version>1.0.0</version>
</dependency>
```

For the server side, the namespace was adopted.

And below code was used to create a Socket object from the client side.

```java
private static Socket connect() {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = true;
            opts.timeout = 5000;
            Manager manager = new Manager(new URI(HOST), opts);
            Socket socket = manager.socket("/car");
            socket.on(Socket.EVENT_CONNECT, objects -> {
                System.out.println(Thread.currentThread().getName());
                System.out.println(socket.id() + " connected. ");
            }).on("message", data -> {
                System.out.println(data[1]);
            }).on(Socket.EVENT_DISCONNECT, objects -> System.out.println(socket.id() + " disconnected")
            ).on(Socket.EVENT_ERROR, objects -> System.out.println(objects.toString()));
            socket.connect();
            return socket;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
```

Then if I connect to the Socket IO server, and then close it manually, an *IllegalStateException* will be thrown from the server side.

```java
private static final String HOST = "http://127.0.0.1";

    public static void main(String[] args) throws InterruptedException {
        Socket socket = connect();
        System.out.println("sleep for 5 seconds and then close socket io.");
        Thread.sleep(5000);
        socket.close();
        Thread.sleep(5000);
    }
```

---

### Root Cause

The root cause for this issue is there is a Bug in socketio-client. If you open the IOParser.java from the source code, and go to the method encodeAsString:

```java
private String encodeAsString(Packet obj) {
            StringBuilder str = new StringBuilder("" + obj.type);

            if (BINARY_EVENT == obj.type || BINARY_ACK == obj.type) {
                str.append(obj.attachments);
                str.append("-");
            }

            if (obj.nsp != null && obj.nsp.length() != 0 && !"/".equals(obj.nsp)) {
                str.append(obj.nsp);
                str.append(","); // here is the bug, when user invoke the Socket.close() method in a namespace scenario /event, the message "41/event," will be send, the comma should be removed here.
            }

            if (obj.id >= 0) {
                str.append(obj.id);
            }

            if (obj.data != null) {
                str.append(obj.data);
            }

            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("encoded %s as %s", obj, str));
            }
            return str.toString();
        }
```



If the message "41/event," was sent to server, the server will parse the namespace /event, and then read the remaining byte buffer "," again, then try to parse Packet Type with value 12, an *IllegalStateException*  will be thrown.

---

### Solution

So we change the code as below:

```java
private String encodeAsString(Packet obj) {
            
			// from =======================
  
            if (obj.nsp != null && obj.nsp.length() != 0 && !"/".equals(obj.nsp)) {
                str.append(obj.nsp);
                str.append(",");
            }
  
  			// to ========================

  			if (obj.nsp != null && obj.nsp.length() != 0 && !"/".equals(obj.nsp)) {
                str.append(obj.nsp);
                if (obj.id >= 0 || obj.data != null) {
                    str.append(",");
                }
            }          
  
            return str.toString();
        }
```

And the close protocol will be sent correct.

Since the git hub was not updated yet, I download the project and built it by myself, and it works.

---

The demo project can be found on [GitHub](https://github.com/zengbiaobiao/demo/edit/master/socketio-demo)
