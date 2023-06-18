FROM node:18-alpine AS builder

WORKDIR /app
COPY package.json .
RUN npm i
COPY . .
RUN npm i -g @angular/cli
RUN npm run build

FROM nginx:1.23.3
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist/ /var/www/frontend/html/