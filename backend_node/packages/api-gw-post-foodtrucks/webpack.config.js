const path = require('path');
const pkg = require('./package.json');

module.exports = {
  target: 'node',
  entry: path.resolve(__dirname, 'src'),
  resolve: {
    extensions: ['.js', '.jsx', '.json', '.ts', '.tsx'],
    modules: [path.resolve(__dirname, '../../node_modules'), path.resolve(__dirname, 'node_modules')],
    symlinks: false
  },
  output: {
    libraryTarget: 'commonjs',
    path: path.resolve(__dirname, 'dist'),
    filename: `index.js`,
  },
  module: {
    rules: [
      {
        test: /\.(t|j)s(x?)$/,
        exclude: /node_modules/,
        use: {
          loader: 'ts-loader',
          options: {
            onlyCompileBundledFiles: true,
          },
        },
      },
    ],
  },
};
