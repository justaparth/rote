import { Container } from 'flux/utils';
import AppView from '../components/AppView';
import CardStore from '../stores/cardStore';

function getStores() {
  return [
    CardStore,
  ];
}

function getState() {
  return {
    cards: CardStore.getState(),
    extraState: [1, 2, 3, 4],
  };
}

export default Container.createFunctional(AppView, getStores, getState);
