<template>
    <v-navigation-drawer app
            v-model="drawer"
            permanent
    >
        <v-list-item class="px-2">
            <v-list-item-avatar>
                <v-img src="https://randomuser.me/api/portraits/men/85.jpg"></v-img>
            </v-list-item-avatar>

            <v-list-item-title>John Leider</v-list-item-title>

            <v-btn icon @click.stop="mini = !mini">
                <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
        </v-list-item>

        <v-divider></v-divider>

        <v-list dense>
            <v-list-item
                    v-for="item in items"
                    :key="item.title"
                    :to="item.to"
                    link
            >
                <v-list-item-icon>
                    <v-icon>{{ item.icon }}</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>
<script>
    export default {
        name: 'DrawerView',
        data: () => ({
            items:[
                {title:'lists',icon:'mdi-magnify',to:'/vtf/lists'},
                {title:'heart',icon:'mdi-heart',to:'/vtf/list1s'},
                {title:'vertical',icon:'mdi-dots-vertical',to:'/vtf/lists2'},
            ],
            mini:1,
            drawer:2
        })
    }
</script>
