import AppView from '../components/AppView.jsx';
import { Container } from 'flux/utils';
import CardStore from '../stores/cardStore.js';

function getStores() {
  return [
    CardStore
  ];
}

function getState() {
  console.log(CardStore.getState().cards);
  return {
    cards: CardStore.getState().cards
  };
}

export default Container.createFunctional(AppView, getStores, getState);
