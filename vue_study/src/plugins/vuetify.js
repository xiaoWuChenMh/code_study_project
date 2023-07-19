import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import colors from 'vuetify/lib/util/colors'

Vue.use(Vuetify);
const theme = {
    primary: '#4E4356',
    secondary: '#4b89dc',
    accent: '#232f34',
    info: '#00CAE3',
    divder: '#121212',
    error: colors.red.base
}

export default new Vuetify({
    theme: {
        themes: {
            dark: theme,
            light: theme

        },
    },
})
