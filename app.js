
const express = require('express');
const chalk = require('chalk');
const debug = require('debug')('app');
const morgan = require('morgan');
const path = require('path');
const { title } = require('process');

const PORT = process.env.PORT || 3000;
const app = express();

app.use(morgan('tiny'));
app.use(express.static(path.join(__dirname,'/public/')));

app.set('views','./src/views');
app.set('view engine','ejs');

app.get('/',(req,res)=>{
   res.render('index',{title: 'the learn space',data: ['gnilapon','mogoum','ebenezaire']});
   //res.send('Hello fron my home in mauritius');
});

app.listen(PORT,()=>{
 debug(`Open app on this port ${chalk.green(PORT)}`);
});