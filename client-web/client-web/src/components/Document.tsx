import { EditOutlined, EllipsisOutlined, SettingOutlined } from "@ant-design/icons";
import { Card } from "antd";

interface DocumentProps {
    name: string,
    description: string,
}

function Document(data: DocumentProps) {
    return (
        <Card className="shadow-lg max-w-80" title={data.name} actions={[
            <SettingOutlined key="setting" />,
            <EditOutlined key="edit" />,
            <EllipsisOutlined key="ellipsis" />,
        ]}>
            <p>{data.description}</p>
        </Card>
    )
}

export default Document;