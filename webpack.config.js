'use strict';

var webpack = require('webpack'),
    jsPath  = 'app/assets/javascripts',
    path = require('path'),
    srcPath = path.join(__dirname, 'app/assets/javascripts');

var config = {
    target: 'web',

    // good.
    entry: {
        app: path.join(srcPath, 'app.jsx')
        //, common: ['react-dom', 'react']
    },

    output: {
        path:path.resolve(__dirname, jsPath, 'build'),
        //publicPath: '',
        filename: '[name].js',
        //pathInfo: true
    },


    resolve: {
        //alias: {},
        //root: srcPath,
        extensions: ['.js'],
        modules: ['node_modules', jsPath]
    },


    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            },
            {
                test: /\.scss$/,
                use: [{
                    loader: "style-loader" // creates style nodes from JS strings
                }, {
                    loader: "css-loader" // translates CSS into CommonJS
                }, {
                    loader: "sass-loader" // compiles Sass to CSS
                }]
            }
        ]
    },
    plugins: [
        //new webpack.optimize.CommonsChunkPlugin('common', 'common.js'),
        //new webpack.optimize.UglifyJsPlugin({
        //    compress: { warnings: false },
        //    output: { comments: false }
        //}),
        new webpack.NoEmitOnErrorsPlugin()
    ]
};

module.exports = config;