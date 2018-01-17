'use strict';

import AppView from '../components/AppView.jsx';
import { Container } from 'flux/utils';
import CardStore from '../stores/cardStore.js';

function getStores() {
  return [
    CardStore
  ];
}

function getState() {
  console.log("getState method in AppContainer");
  console.log(CardStore.getState());
  return {
    cards: CardStore.getState(),
    extraState: [1, 2, 3, 4]
  };
}

export default Container.createFunctional(AppView, getStores, getState);
