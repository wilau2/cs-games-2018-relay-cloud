FROM alpine:edge
MAINTAINER mousaa@uoguelph.ca
RUN apk add --no-cache openjdk8
COPY authorization/* \
  build/authorization/

FROM alpine:edge
MAINTAINER mousaa@uoguelph.ca
RUN apk add --no-cache openjdk8
COPY config-server/* \
  build/config-server/

FROM alpine:edge
MAINTAINER mousaa@uoguelph.ca
RUN apk add --no-cache openjdk8
COPY eureka-server/* \
  build/eureka-server/

FROM alpine:edge
MAINTAINER mousaa@uoguelph.ca
RUN apk add --no-cache openjdk8
COPY zuul-server/* \
  build/zuul-server/

FROM alpine:edge
MAINTAINER mousaa@uoguelph.ca
RUN apk add --no-cache openjdk8
COPY communication/* \
  build/communication/

