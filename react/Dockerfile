FROM node:20-alpine AS builder
WORKDIR /app

RUN apk add --no-cache python3 make g++ && \
    npm install -g vite

COPY ./react/package*.json ./
RUN npm install

COPY ./react/ ./
COPY ./react/postcss.config.js ./postcss.config.js
COPY ./react/tailwind.config.js ./tailwind.config.js

RUN vite build

FROM nginx:1.27.4-alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
