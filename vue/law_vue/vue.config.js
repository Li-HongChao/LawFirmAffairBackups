const {defineConfig} = require('@vue/cli-service')

module.exports = defineConfig({
    transpileDependencies: true,
    //开启代理服务器(方式一)
    devServer: {
        port: 8082,
        //请求服务器的地址
        proxy: 'http://localhost:8082',
    }
})
