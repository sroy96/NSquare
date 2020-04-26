import React, { Component } from 'react'
import { Menu, Affix, AutoComplete, Avatar, Row, Col } from 'antd';
import {
  CodeOutlined
} from '@ant-design/icons';
import {withRouter} from 'react-router-dom';

class Nav extends Component {

  constructor(props){
    super(props);
    console.log(this);
  }

  componentDidMount() {
    
    }


    redirectUser = () => {
      this.props.history.push("/profile");
    }

    redirectHome = () => {
      this.props.history.push("/home");
    }

  render() {

    const options = [
      { value: 'easy and medium facebook questions' },
      { value: 'hard google dp questions ' },
      { value: 'linked list and trees ' },
    ];
  
    return (
      <Affix>
      <Menu  mode="horizontal" >
        <Row>
          <Col span={8}>
            <div style={{textAlign : 'left', marginLeft : '5%'}}>
            <Menu.Item key="mail" onClick={this.redirectHome}>
            {/* <CodeOutlined /> */}
              Coder Pad
            </Menu.Item>
            </div>
          </Col>
          <Col span={8}>
            <div style={{textAlign : 'center'}}>
              {window.location.pathname === "/home" ?
              <AutoComplete
              options={options}
              style={{ width: 400, marginTop: '2%', marginBottom : '2%' }}
              // onSelect={onSelect}
              // onSearch={onSearch}
              placeholder="Search here !" 
            /> : null
            }
            </div>  
          </Col>
          <Col span={8}> 
            <div style={{textAlign : 'right'}}>
              {window.location.pathname !== "/login"  ?
              <Avatar style={{ backgroundColor: '#87d068', marginRight : '10%' }} src={window.localStorage.getItem("profile-photo")} onClick={this.redirectUser} /> : null
              }
            </div>
          </Col>
        </Row>
      </Menu>
      </Affix>
    )
  }
}

export default withRouter(Nav);
