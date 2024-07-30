
const express = require('express');
const chalk = require('chalk');
const debug = require('debug')('app');
const morgan = require('morgan');
const path = require('path');

const app = express();

app.use(morgan('any'));
app.use(express.static(path.join(__dirname,'/public/')));

app.get('/',(req,res)=>{
   res.send('Hello fron my home in mauritius');
});

app.listen(3000,()=>{
 debug(`Open on the port ${chalk.green('3000')}`);
});