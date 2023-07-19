<template>
    <v-app-bar app
            color="#6A76AB"
            dark
            src="https://picsum.photos/1920/1080?random"
    >
        <template v-slot:img="{ props }">
            <v-img
                    v-bind="props"
                    gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
            ></v-img>
        </template>

        <v-app-bar-nav-icon></v-app-bar-nav-icon>

        <v-app-bar-title>面向未来</v-app-bar-title>

        <v-spacer></v-spacer>

        <v-btn icon>
            <v-avatar size="70">
                <v-img src="https://randomuser.me/api/portraits/men/85.jpg"></v-img>
            </v-avatar>
        </v-btn>

        <v-spacer></v-spacer>
        <v-btn icon>
            <v-icon>mdi-magnify</v-icon>
        </v-btn>

        <v-btn icon>
            <v-icon>mdi-heart</v-icon>
        </v-btn>

        <v-btn icon>
            <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>

        <template v-slot:extension>
            <v-tabs align-with-title>
                <v-tab>chat</v-tab>
                <v-tab>导航</v-tab>
                <v-tab>暂定</v-tab>
            </v-tabs>
        </template>
    </v-app-bar>
</template>
<script>
    export default {
        name: 'HeadView',
        data: () => ({
            name:'HeadView'
        })
    }
</script>
