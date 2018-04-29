java -cp target\rvshop-0.7.0-SNAPSHOT.jar com.via.rv.RvShopApplication db migrate config\staging.yml
java -cp target\rvshop-0.7.0-SNAPSHOT.jar com.via.rv.RvShopApplication server config\staging.yml
curl -s -k http://localhost:8082/hello-world


    GET     /hello-world (com.via.rv.resources.HelloWorldResource)
    POST    /hello-world (com.via.rv.resources.HelloWorldResource)
    GET     /views/iso88591.ftl (com.via.rv.resources.ViewResource)
    GET     /views/iso88591.mustache (com.via.rv.resources.ViewResource)
    GET     /views/utf8.ftl (com.via.rv.resources.ViewResource)
    GET     /views/utf8.mustache (com.via.rv.resources.ViewResource)
    GET     /protected (com.via.rv.resources.ProtectedResource)
    GET     /people (com.via.rv.resources.PeopleResource)
    POST    /people (com.via.rv.resources.PeopleResource)
    GET     /people/{personId} (com.via.rv.resources.PersonResource)

