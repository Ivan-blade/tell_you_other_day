module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  devServer: {
    host: '0.0.0.0',
    port: 8080,
    proxy: {
        'api': {
            target: 'http://localhost:9090',
            changeOrigin: false,
            pathRewrite: {
                '/api': '/'
            }
        }
    }
  }
}