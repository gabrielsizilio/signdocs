import useAuth from '../../hooks/useAuth'

export default function ProtectedLayout({ children }: { children: JSX.Element }) {
    const auth = useAuth();

    if (!auth.email) {
        // TODO: Retornar página de unauthorized
        return (
            <h1> You don't have access </h1>
        );
    }

    return children;
}
