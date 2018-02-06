import { Container } from 'flux/utils';
import AppView from '../components/AppView';
import CardStore from '../stores/cardStore';
import DeckStore from '../stores/deckStore';

function getStores() {
  return [
    CardStore,
    DeckStore,
  ];
}

function getState() {
  return {
    cards: CardStore.getState(),
    decks: DeckStore.getState(),
    extraState: [1, 2, 3, 4],
  };
}

export default Container.createFunctional(AppView, getStores, getState);
