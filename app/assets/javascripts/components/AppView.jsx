import React from 'react';
import { HashRouter, Switch, Route } from 'react-router-dom';
import Cards from './Cards.jsx';
import Header from '../components/Header.jsx';

class Home extends React.Component {
  render() {
    console.log(this.props);
    return (<p> Home Element </p>)
  }
}

class Reviews extends React.Component {
  render() {
    console.log(this.props);
    return (<p> Reviews Element </p>)
  }
}

function Temp(props) {
  return (
    <div>
      <Header />
      <Switch>
        <Route exact path='/' render={() => <Home  {...props}/>} />
        <Route path='/reviews' render={() => <Reviews {...props}/>} />
        <Route path='/cards' render={() => <Cards {...props}/>} />
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
