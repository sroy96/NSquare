import React, {
    Component
} from 'react'
import {
    BackTop,
    Rate,
    Card,
} from 'antd';
import {EditOutlined } from '@ant-design/icons';
import {withRouter} from 'react-router-dom';


class Paper extends Component {


    constructor(props) {
        super(props);
        this.state = {
            rate_value : 1
        }
    }

    questionPage = () => {
        this.props.history.push("/question/" + this.props.question.document_id);
    };

    updateRating = (value) => {
        this.setState({rate_value : value});
        console.log(value);
    }


    render() {
        let v = this.props.question;
            return ( 
            <div> 
                <BackTop /> 
                <Card title={v.company_name}
                actions={[
                    <EditOutlined key="solve" onClick={this.questionPage} />,
                    <Rate key="rate" value={this.state.rate_value} onChange={this.updateRating} />,
                  ]}
                style = {{
                        marginLeft: '2.5%',
                        marginRight: '2.5%',
                        marginTop: '2.5%'
                    }} >
                        <p>
                            {v.question_base}
                        </p>
                </Card> 
            </div>
            )
        }
    }

export default withRouter(Paper);