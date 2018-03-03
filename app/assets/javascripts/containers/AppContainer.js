import { Container } from 'flux/utils';
import AppView from '../components/AppView';
import CardStore from '../stores/cardStore';
import DeckStore from '../stores/deckStore';

// TODO: This is probably NOT the right way to load data. Anyways, figure it out later.
import DeckActions from '../actions/deckActions';

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

DeckActions.loadDecksForUser(1);

export default Container.createFunctional(AppView, getStores, getState);
