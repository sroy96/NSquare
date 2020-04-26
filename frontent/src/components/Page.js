import React, { Component } from 'react'
import Paper from './Paper.js';
import { Row, Col } from 'antd';

const t = [
  {
    company_name : "facebook",
    question_tag : ["array", "dp"],
    question_difficulty : "hard",
    question_base : "find the longest subsequence ",
    full_question : "some markdown content will be available here .... "
  },
  {
    company_name : "bloomberg",
    question_tag : ["2 pointer", "dp"],
    question_difficulty : "easy",
    question_base : "find the smallest set ",
    full_question : "some markdown content will be available here .... "
  },
  {
    company_name : "google",
    question_tag : ["matrix", "dfs"],
    question_difficulty : "medium",
    question_base : "find islands ",
    full_question : "some markdown content will be available here .... "
  },
  {
    company_name : "facebook",
    question_tag : ["bfs", "sorting"],
    question_difficulty : "hard",
    question_base : "find the correct letter ",
    full_question : "some markdown content will be available here .... "
  }

];

export default class Page extends Component {

  constructor(props) {
    super(props);
    this.state = {
      q : t,
    };
  }

  componentDidMount() {

    const requestOptions = {
      method: 'GET',
      headers: { 'Content-Type': 'application/json',
              'Access-Control-Allow-Origin' : '*',
                'Access-Control-Allow-Credentials' : 'true',
              'access_token' : localStorage.getItem('token') 
            }
  };
  fetch('http://192.168.0.103:3005/questions', requestOptions)
    .then((res) => res.json())
    .then(v => {
      console.log(v);
      this.setState({q : v.data})
    })
    .catch(() => {
      this.props.history.push("/login");
    });
  }

  cards() {
    let rows = [];
    this.state.q.map((item, index) => {
      rows.push(<Col key={index} span={8}>
          <Paper question={item} />
          </Col>);

    });
    return rows;
  }

  render() {

    return (
      <div>
        <Row gutter={[10, 10]}>
        {this.cards()}
        </Row>
      </div>
    )
  }
}
