FROM nginx:1.21.4-alpine

# Remove o arquivo de configuracao padrao do NGINX
RUN rm /etc/nginx/conf.d/default.conf

# Copia o arquivo customizad para o arquivo de configucao padro do NGINX
COPY nginx.conf /etc/nginx/conf.d/default.conf
