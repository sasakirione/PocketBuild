import {AppBar, Avatar, Typography, Button} from "@mui/material";
import {NextPage} from "next";
import {useAuth0} from "@auth0/auth0-react";
import styles from './Header.module.css'

const AuthButton = () => {
    const {
        isAuthenticated,
        loginWithRedirect,
        logout,
        user
    } = useAuth0();

    return (
        <div className={styles.auth}>
            {isAuthenticated && (
                <div className={styles.authRow}>
                    <Avatar alt="User Icon" src={user?.picture}/>
                    <Button color="inherit" onClick={() => logout()}>Logout</Button>
                </div>
            )}
            {!isAuthenticated && (
                <Button color="inherit" onClick={() => loginWithRedirect()}>Login</Button>
            )}
        </div>
    )
}

const Header: NextPage = () => {

    return (
        <AppBar className={styles.header} position="static">
            <Typography
                variant="h5"
                component="div"
                id="header-logo"
            >
                ぽけっとビルド
            </Typography>
            <Button href="/build" color="inherit" id="header-link-build">編成</Button>
            <Button href="/pokemon" color="inherit" id="header-link-pokemon">ポケモン一覧</Button>
            <Button href="/data" color="inherit" id="header-link-data">ポケモンデータ</Button>
            <Button href="/information" color="inherit" id="header-link-info">情報</Button>
            <AuthButton/>
        </AppBar>
    )
}

export default Header