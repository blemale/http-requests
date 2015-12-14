# HttpRequests: Factory for fluent-hc which enforce sensible timeouts

## Motivations

http://dev.bizo.com/2013/04/sensible-defaults-for-apache-httpclient.html

## Exemple

    Response response = HttpRequests.Get("http://www.google.com", Timeouts.NORMAL).execute();