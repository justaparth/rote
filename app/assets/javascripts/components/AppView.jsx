import React from 'react';
import { HashRouter, Switch, Route } from 'react-router-dom';
import Decks from './Decks.jsx';
import DeckDisplay from './DeckDisplay.jsx';
import Home from './Home.jsx';
import Reviews from './Reviews.jsx';
import AppBar from 'material-ui/AppBar';
import Tabs, { Tab } from 'material-ui/Tabs';
import { Link } from 'react-router-dom'

import Reboot from 'material-ui/Reboot';

function Temp(props) {
  return (
    <div>
      <AppBar position="static">
        <Tabs>
          <Tab label="Home" component={Link} to='/' />
          <Tab label="Decks" component={Link} to='/decks' />
          <Tab label="Reviews" component={Link} to='/reviews' />
        </Tabs>
      </AppBar>
      <Switch>
        <Route exact path='/' render={() => <Home  {...props}/>} />
        {/* fix this */}
        <Route exact path='/decks' render={() => <Decks {...props}/>} />
        <Route exact path='/decks/' render={() => <Decks {...props}/>} />
        <Route path='/decks/:deckId' render={(routerProps) => <DeckDisplay {...routerProps} {...props}/>} />
        <Route path='/reviews' render={() => <Reviews {...props}/>} />
      </Switch>
    </div>
  )
}

function AppView(props) {
  return (
    <main>
      <HashRouter >
        <Temp {...props} />
      </HashRouter>
    </main>
  )
}

export default AppView;
