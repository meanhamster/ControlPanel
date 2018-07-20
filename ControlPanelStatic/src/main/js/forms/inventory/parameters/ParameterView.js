import React from 'react';
import {TextInput} from "../../../components/basic/inputs/TextInput";
import {UniformGrid} from "../../../components/basic/formatters/UniformGrid";

export class ParameterView extends React.Component {

    render() {
        const {name, type, value} = this.props;
        return <UniformGrid>
            {name && <TextInput label={"Название параметра"} value={name}/>}
            {value && <TextInput label={"Значение параметра"} value={value}/>}
            {type && <TextInput label={"Тип параметра"} value={type}/>}
        </UniformGrid>;
    }
}