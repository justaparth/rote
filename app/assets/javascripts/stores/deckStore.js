/* eslint class-methods-use-this: 0 */

import { ReduceStore } from 'flux/utils';
import Immutable from 'immutable';
import Dispatcher from '../dispatcher';
import ActionTypes from '../constants/index';

class DeckStore extends ReduceStore {
  constructor() {
    super(Dispatcher);
  }

  /**
   * Deck structure:
   * { name: '', cards: [], author_id: <int> }
   *
  */
  getInitialState() {
    return Immutable.Map();
  }

  areEqual() {
    // TODO: understand about this method
    // TODO: a,b are the params
    return false;
  }

  reduce(state, action) {
    switch (action.actionType) {
      case ActionTypes.CREATE_CARD: {
        const { card } = action;
        const newDeck = state.get(card.deckId);
        newDeck.append(card);
        return state.set(card.deckId, newDeck);
      }
      case ActionTypes.LOADED_DECKS_FOR_USER: {
        const { decks } = action;
        return decks.reduce(
          (map, deck) => map.set(deck.id, deck),
          Immutable.Map(),
        );
      }
      case ActionTypes.LOADED_DECK: {
        const { cards, deckId } = action;
        const deck = state.get(deckId);
        deck.cards = cards;
        return state.set(deckId, deck);
      }
      default: {
        return null;
      }
    }
  }
}

export default new DeckStore();
