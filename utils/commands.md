Some useful commands:

* ``docker logs -f $(docker ps -qf "name=marvelous")`` : finds the container ID of a container which contains the word "marvelous" in its container name, and then launches a real-time following of its internal logs.